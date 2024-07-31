package ru.yorymoto.photogallery.model

import com.fasterxml.jackson.annotation.JsonProperty

class OneDriveResponse {
    @JsonProperty("@odata.context")
    String odataContext
    CreatedBy createdBy
    String createdDateTime
    String cTag
    String eTag
    String id
    LastModifiedBy lastModifiedBy
    String lastModifiedDateTime
    String name
    ParentReference parentReference
    long size
    String webUrl
    FileSystemInfo fileSystemInfo
    Folder folder
    Reactions reactions
    Shared shared
    @JsonProperty("children@odata.context")
    String childrenOdataContext
    @JsonProperty("children@odata.count")
    int childrenOdataCount
    List<Child> children
}

class CreatedBy {
    Application application
    Device device
    User user
    OneDriveSync oneDriveSync
}

class Application {
    String displayName
    String id
}

class Device {
    String id
}

class User {
    String displayName
    String id
}

class OneDriveSync {
    @JsonProperty("@odata.type")
    String odataType
    String id
}

class LastModifiedBy {
    User user
}

class ParentReference {
    String driveId
    String driveType
}

class FileSystemInfo {
    String createdDateTime
    String lastModifiedDateTime
}

class Folder {
    int childCount
    FolderView folderView
    String folderType
}

class FolderView {
    String viewType
    String sortBy
    String sortOrder
}

class Reactions {
    int commentCount
}

class Shared {
    List<String> effectiveRoles
    Owner owner
    String scope
    String sharedDateTime
}

class Owner {
    User user
}

class Child {
    @JsonProperty("@content.downloadUrl")
    String contentDownloadUrl
    CreatedBy createdBy
    String createdDateTime
    String cTag
    String eTag
    String id
    LastModifiedBy lastModifiedBy
    String lastModifiedDateTime
    String name
    ParentReference parentReference
    long size
    String webUrl
    File file
    FileSystemInfo fileSystemInfo
    Reactions reactions
    ImageForPage image
    Photo photo
    Rating rating
}

class File {
    Hashes hashes
    String mimeType
}

class Hashes {
    String quickXorHash
    String sha1Hash
    String sha256Hash
}

class Image {
    int height
    int width
}

class Photo {
    String cameraMake
    String cameraModel
    int exposureDenominator
    int exposureNumerator
    double focalLength
    double fNumber
    int iso
    String takenDateTime
}

class Rating {
    int rating
    int simpleRating
}
