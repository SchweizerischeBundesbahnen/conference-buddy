package ch.sbb.confbuddy.rest.controller;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.sbb.confbuddy.model.Comment;
import ch.sbb.confbuddy.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * <pre>
 *
 * @author Gilles Zimmermann
 * @code {
 * "id": "12345",                          // wird bei PUT vom Backend generiert, identifiziert Kommentar
 * "pid": "77",                            // vortrag-Id
 * "author": {"name": "John Doe"},
 * "comment": "Good talk!",
 * "timestamp": "2014-09-08T18:25:00.000Z"
 * }
 * <p>
 * </pre>
 * <p>
 * Comments:
 * GET /comment/{pid}
 * PUT /comment
 * POST /comment/{cid}
 * DELETE/comment/{cid}
 * @since 0.0.1, 2014
 */
@Component
@Path("/comment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {

  private CommentService commentService;

  /**
   * init {@code Autowired} fields.
   */
  @Autowired
  public CommentResource(final CommentService commentService) {
    this.commentService = commentService;
  }

  /**
   * Liefert alle Kommentare des Vortrags {pid}.
   * Rückgabe: Array von Kommentare. Die Kommentare sind chronologisch nach Erstellungszeitpunkt sortiert,
   * vom Letzten zum Ersten. Falls keine Kommentare existieren, wird ein leeres Array zurückgeliefert.
   *
   * @param pid Vortrag-ID
   */
  @GET
  @Path("/{pid}")
  public Comment[] loadAll(@PathParam("pid") final String pid) {
    final List<Comment> commentList = commentService.loadAll(pid);

    if (commentList == null) {
      return new Comment[0];
    }
    return commentList.toArray(new Comment[commentList.size()]);
  }

  /**
   * Speichert den Kommentar in der DB. Der Kommentar wird mit PUT ohne id geliefert. Das Backend erzeugt
   * die eindeutige id beim Speichern. user-token wird verlangt.
   *
   * @return Kommentar mit der Id.
   */
  @PUT
  @Path("/")
  public Comment save(final Comment comment,
                      @HeaderParam("X-Access-Token") String userId) {
    return commentService.save(comment, userId);
  }

  /**
   * Aktualisiert den Kommentar mit {cid} in der DB. user-token wird verlangt.
   */
  @POST
  @Path("/{cid}")
  public void update(@PathParam("cid") final long id, final String comment,
                     @HeaderParam("X-Access-Token") String userId) {
    commentService.update(id, comment, userId);
  }

  /**
   * Löscht den Kommentar im Backend. user-token wird verlangt. Validierung: Ein User darf ausschliesslich
   * ein eigenen Kommentar löschen.
   */
  @DELETE
  @Path("/{cid}")
  public void delete(@PathParam("cid") final long id, @HeaderParam("X-Access-Token") String userId) {
    commentService.delete(id, userId);
  }
}
