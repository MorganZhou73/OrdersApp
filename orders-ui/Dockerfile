#stage 1
FROM node:latest as node
RUN mkdir -p /app
WORKDIR /app
COPY . /app
RUN npm install
RUN npm run build --prod

#stage 2
FROM nginx:latest
COPY --from=node /app/dist/orders-ui /usr/share/nginx/html
EXPOSE 80
