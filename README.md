# Vaadin adapter for TinyMCE rich text format editor 

Realised using web components

## Development instructions

### Important Files 
frontend/src/tinymce-loader.js - loader of tinyMCE. 
Needs to be included to your ./frontend/src folder in your project.
### Deployment

Starting the test/demo server:
```
mvn jetty:run
```

This deploys demo at http://localhost:8080
 
### Integration test

To run Integration Tests, execute `mvn verify -Pit,production`.

## Publishing to Vaadin Directory

You should change the `organisation.name` property in `pom.xml` to your own name/organization.

```
    <organization>
        <name>###author###</name>
    </organization>
```

You can create the zip package needed for [Vaadin Directory](https://vaadin.com/directory/) using

```
mvn versions:set -DnewVersion=1.0.0 ~~~~# You cannot publish snapshot versions 
~~mvn install -Pdirectory~~
```

The package is created as `target/{project-name}-1.0.0.zip`

For more information or to upload the package, visit https://vaadin.com/directory/my-components?uploadNewComponent
