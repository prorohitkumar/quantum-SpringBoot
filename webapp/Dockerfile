FROM node:15.9.0-alpine
WORKDIR /app
COPY . .
RUN npm install
# RUN npm install http-server -g
RUN npm install -g @angular/cli@11.0.2
RUN npm run build --prod
WORKDIR /app/dist/webapp
# CMD npm start
CMD ng serve --host 0.0.0.0