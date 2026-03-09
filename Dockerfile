# syntax=docker/dockerfile:1

#docker run -it --rm --entrypoint sh node:25-alpine
# docker compose up --build

# Base image
FROM node:25-alpine

# Verify versions
RUN echo "NODE VERSION = $(node -v)"
RUN echo "NPM VERSION = $(npm -v)"

# Use production node environment by default.
ENV NODE_ENV production


WORKDIR /home/node/app

# Download dependencies as a separate step to take advantage of Docker's caching.
# Leverage a cache mount to /root/.npm to speed up subsequent builds.
# Leverage a bind mounts to package.json and package-lock.json to avoid having to copy them into
# into this layer.
# RUN --mount=type=bind,source=package.json,target=package.json \
#     --mount=type=bind,source=package-lock.json,target=package-lock.json \
#     --mount=type=cache,target=/root/.npm \
#     npm ci --omit=dev

# Run the application as a non-root user.
USER node

# Copy the rest of the source files into the image.
COPY . .

# Expose the port that the application listens on.
EXPOSE 8080

# Run the application.
CMD node test.js
