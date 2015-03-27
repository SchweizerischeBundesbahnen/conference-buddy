package ch.sbb.confbuddy.rest.controller;

import java.util.List;

import ch.sbb.confbuddy.model.Comment;
import ch.sbb.confbuddy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service")
public class CommentController {
  @Autowired
  private CommentService commentService;

  /**
   * Liefert alle Kommentare des Vortrags {pid}.
   * Rückgabe: Array von Kommentare. Die Kommentare sind chronologisch nach Erstellungszeitpunkt sortiert,
   * vom Letzten zum Ersten. Falls keine Kommentare existieren, wird ein leeres Array zurückgeliefert.
   *
   * @param pid Vortrag-ID
   */
  @RequestMapping("comment/{pid}")
  public
  @ResponseBody
  List<Comment> loadAll(@PathVariable final String pid) {
    return commentService.loadAll(pid);
  }

  /**
   * Speichert den Kommentar in der DB. Der Kommentar wird mit PUT ohne id geliefert. Das Backend erzeugt
   * die eindeutige id beim Speichern. user-token wird verlangt.
   *
   * @return Kommentar mit der Id.
   */

  @RequestMapping(value = "comment", method = RequestMethod.PUT)
  public
  @ResponseBody
  Comment save(@RequestBody final Comment comment,
               @RequestHeader("X-Access-Token") String userId) {
    return commentService.save(comment, userId);
  }

  /**
   * Aktualisiert den Kommentar mit {cid} in der DB. user-token wird verlangt.
   */
  @RequestMapping(value = "comment/{cid}", method = RequestMethod.POST)
  public void update(@PathVariable("cid") final long id,
                     @RequestBody final String comment,
                     @RequestHeader("X-Access-Token") String userId) {
    commentService.update(id, comment, userId);
  }

  /**
   * Löscht den Kommentar im Backend. user-token wird verlangt. Validierung: Ein User darf ausschliesslich
   * ein eigenen Kommentar löschen.
   */
  @RequestMapping(value = "comment/{cid}", method = RequestMethod.DELETE)
  public void delete(@PathVariable("cid") final long id,
                     @RequestHeader("X-Access-Token") String userId) {
    commentService.delete(id, userId);
  }
}
