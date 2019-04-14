# Quarkus PDF Extract

An example microservice which extracts the text contents of uploaded PDF files.

It is built using [Quarkus](http://quarkus.io/) and uses [Apache PDFBox](https://pdfbox.apache.org/)
as well as Jonathan Link's [PDFLayoutTextStripper](https://github.com/JonathanLink/PDFLayoutTextStripper).

## Building and Running the Service

This service is intended to be run as a native Linux binary via GraalVM.
Build the binary like so:

    ./mvnw package -Pnative -Dnative-image.container-runtime=docker

Then create a Docker container with the binary:

    docker build -f src/main/docker/Dockerfile.native -t quarkus-examples/quarkus-pdf-export .
Run the container:

    docker run -i --rm -p 8080:8080 -e PORT=8080 quarkus-examples/quarkus-pdf-export

## Running in Dev Mode

While working on the service, the Quarkus Dev Mode comes in handy:

    ./mvnw compile quarkus:dev

Modify source code and invoke the service again (see below), and it will automatically be re-compiled.

## Invoking the Service

To invoke the service, e.g. use httpie like so (adjust the file name to a PDF on your hard disk):

    http -f POST localhost:8080/rest/extract pdfFile@"/path/to/some/file.pdf" -d

This will create a file _extracted.txt_ with the extracted text in the current directory.

Alternatively, open http://localhost:8080 in your web browser, select a PDF file to upload and click "Submit".
This returns a file with the extracted text which you can store to your hard disk.

## Deploying to Google Cloud Run

The Quarkus-built native binary is a perfect fit for Serverless environments such as Google Cloud Run.
Follow the steps in the Google Cloud Run [documentation](https://cloud.google.com/run/docs/quickstarts/build-and-deploy)
for setting up Cloud Run and the Google Cloud SDK.

Submit a build of the Docker container to the Google Container Registry:

    rm -rf target/reports
    gcloud builds submit --tag gcr.io/<your project id>/quarkus-pdf-extract

Then deploy an instance of the service to Cloud Run:

    gcloud beta run deploy --image gcr.io/<your project id>/quarkus-pdf-extract

You can then invoke the service as shown before, replacing localhost:8080 with the endpoint shown in the output of the `deploy` command (similar to https://quarkus-pdf-extract-<random part>-uc.a.run.app).
