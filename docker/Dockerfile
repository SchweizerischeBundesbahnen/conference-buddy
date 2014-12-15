#
# Node.js w/ Bower & Grunt Dockerfile
#
# https://github.com/dockerfile/nodejs-bower-grunt
#
# Pull base image.
FROM dockerfile/nodejs-bower-grunt

EXPOSE 9000

#Getting and Running the conference buddy
RUN git clone https://github.com/SchweizerischeBundesbahnen/conference-buddy.git . && git checkout -b feature/sg/issue152 &&  git pull origin feature/sg/issue152 && npm install --unsafe-perm

# Define default command.
CMD ["npm", "start"]