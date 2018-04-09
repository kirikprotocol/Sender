# Sender (Bulk Dispatcher)
## Goal
To send a message simultaneously to all users that ever entered the service.

## Usage

To call the plugin use the link of this kind: ```http://plugins.miniapps.run/sender-plugin?....``` 

For example:
```
<page version="2.0">
  <navigation>
    <link pageId="http://plugins.miniapps.run/sender-plugin>">
       notify all
    </link>
  </navigation>
</page>
```
## Parameters
|Parameter  |Mandatory  |Description                                               |
|:----------|:---------:|:---------------------------------------------------------|
|exit_url   |	Yes       |The URL to return to after using the plugin               |
|locale     |	No	      |Message language (supported languages RU, EN - default)   |
