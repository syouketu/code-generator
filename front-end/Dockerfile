FROM node:lts-alpine as build-stage
ARG PROFILE
WORKDIR /app
COPY package*.json ./
RUN npm install -g cnpm --registry=https://registry.npm.taobao.org
RUN cnpm install
COPY . .
RUN npm run build:${PROFILE}

# production stage
FROM nginx:stable-alpine as production-stage
ARG PROFILE
COPY nginx/nginx-${PROFILE}.conf /etc/nginx/nginx.conf
COPY --from=build-stage /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
