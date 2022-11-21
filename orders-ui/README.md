# Orders-Ui

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 13.1.2.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Build

$ ng build

The build artifacts will be stored in the `dist/` directory.


## Running on Docker

$ docker build -t zmg9046/orders-ui:tag-1.0.0 -f Dockerfile .

$ winpty docker run -d -it -p 4200:80/tcp --name orders-ui zmg9046/orders-ui:tag-1.0.0

