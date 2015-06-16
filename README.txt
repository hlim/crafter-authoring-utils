1) Create a folder with contents to upload. Make sure to match file locations in the repository for existing contents
e.g. 
my-folder/site/website/my-page/index.xml
my-folder/site/components/my-comp-folder/my-component.xml
my-folder/static-assets/images/my-image.jpg
my-folder/templates/web/my-template.ftl

2) Execute authoring-utils-executable-1.0.jar
java -jar authoring-utils-executable-1.0.jar [alfrescoUrl] [folderPath] [site] [username] [password]
e.g.
java -jar authoring-utils-executable-1.0.jar http://localhost:8080/alfresco my-folder acme username password