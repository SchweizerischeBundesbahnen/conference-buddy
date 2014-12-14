#
# Node.js w/ Bower & Grunt Dockerfile
#
# https://github.com/dockerfile/nodejs-bower-grunt
#
# Pull base image.
FROM dockerfile/nodejs-bower-grunt

EXPOSE 9000

RUN git config --global http.sslVerify false

#Getting and Running the conference buddy
RUN git clone https://git.hildebox.org:9000/sebi/conferencebuddy.git . && npm install --unsafe-perm

# Define default command.
CMD ["bash"]