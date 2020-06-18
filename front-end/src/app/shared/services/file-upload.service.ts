import { Injectable } from '@angular/core';
// import * as AWS from 'aws-sdk/global';
import * as S3 from 'aws-sdk/clients/s3';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  constructor() { }

  uploadVideo(file, fileName) {
    const contentType = file.type;
    const bucket = new S3(
      {
        accessKeyId: 'AKIA5M3NQV6LPKYNTAWB',
        secretAccessKey: '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',
        region: 'us-east-1'
      }
    );
    const params = {
      Bucket: 'streaming-video-storage',
      Key: 'videos/' + fileName + '.' + (file.type + '').split('/')[1],
      Body: file,
      ACL: 'public-read',
      ContentType: contentType
    };
    var upload = bucket.upload(params);
    var promise = upload.promise();
    return promise.then(function (data) { return data.Location }, function (err) { return err });
  }

  uploadPhoto(file, fileName) {
    const contentType = file.type;
    const bucket = new S3(
      {
        accessKeyId: 'AKIA5M3NQV6LPKYNTAWB',
        secretAccessKey: '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',
        region: 'us-east-1'
      }
    );
    const params = {
      Bucket: 'streaming-video-storage',
      Key: 'photos/' + fileName + '.' + (file.type + '').split('/')[1],
      Body: file,
      ACL: 'public-read',
      ContentType: contentType
    };
    var upload = bucket.upload(params);
    var promise = upload.promise();
    return promise.then(function (data) { return data.Location }, function (err) { return err });
  }

  uploadCover(file, fileName) {
    const contentType = file.type;
    const bucket = new S3(
      {
        accessKeyId: 'AKIA5M3NQV6LPKYNTAWB',
        secretAccessKey: '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',
        region: 'us-east-1'
      }
    );
    const params = {
      Bucket: 'streaming-video-storage',
      Key: 'covers/' + fileName + '.' + (file.type + '').split('/')[1],
      Body: file,
      ACL: 'public-read',
      ContentType: contentType
    };
    var upload = bucket.upload(params);
    var promise = upload.promise();
    return promise.then(function (data) { return data.Location }, function (err) { return err });
  }

}
