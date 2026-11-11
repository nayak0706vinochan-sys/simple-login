FROM nginx:alpine
COPY src/main/resources/web/login.html /usr/share/nginx/html/index.html
EXPOSE 80