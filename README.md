# KFoenix

KFoenix is a library that defines a set of DSLs for JFoenix library. This library is to be used in conjuntion of https://github.com/edvin/tornadofx. TornadoFX accelerates development of JavaFX applications. Check out the wiki and test folder for examples of each component use available.

## Important
TornadoFX is not yet compatible with Java 9+. This library will be depending on JFoenix for Java 8. 

## Add KFoenix to your project

Currently KFoenix is only available via the sonatype's snapshot repository so you must add the url 
for the repository. I will add KFoenix to mavenCentral once its at 0.1.3 which should be done shortly. Until then
use the snapshot.

Maven
```
 <repositories>
   <repository>
     <id>snapshots-repo</id>
     <url>https://oss.sonatype.org/content/repositories/snapshots</url>
     <releases><enabled>false</enabled></releases>
     <snapshots><enabled>true</enabled></snapshots>
   </repository>
 </repositories>
 
<dependency>
  <groupId>com.github.bkenn</groupId>
  <artifactId>kfoenix</artifactId>
  <version>0.1.3-SNAPSHOT</version>
</dependency>
```

Gradle
```gradle
repositories {
     maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
}

dependencies {
   compile("com.github.bkenn:kfoenix:0.1.3-SNAPSHOT")
}
```

## What is currently implemented?

- [X] JFXAlert
- [X] JFXBadge
- [X] JFXButton
- [X] JFXCheckBox
- [X] JFXColorPicker
- [X] JFXComboBox
- [X] JFXDatePicker
- [X] JFXDecorator
- [X] JFXDialog
- [ ] JFXDialogLayout
- [ ] JFXDrawer 
- [ ] JFXDrawersStack
- [X] JFXHamburger
- [ ] JFXListCell
- [X] JFXListView
- [X] JFXMasonryPane
- [x] JFXNodesList
- [X] JFXPasswordField
- [X] JFXPopup
- [X] JFXProgressBar
- [X] JFXRadioButton
- [X] JFXSpinner
- [ ] JFXScrollPane
- [X] JFXSlider
- [X] JFXSnackbar
- [X] JFXTabPane
- [X] JFXTextArea
- [X] JFXTextField
- [X] JFXTimePicker
- [X] JFXToggleButton
- [X] JFXTogglePane
- [X] JFXToolBar
- [ ] JFXTreeCell
- [ ] JFXTreeTableColumn
- [ ] JFXTreeTableRow
- [ ] JFXTreeTableView
- [ ] JFXTreeViewPath

 
