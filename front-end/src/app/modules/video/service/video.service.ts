import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ContextService } from 'src/app/shared/services/context.service';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  url: string;

  constructor(private httpClient: HttpClient, private contextService: ContextService) {
    this.url = environment.urlVideo;
  }

  getVideo(videoId: number): Observable<any> {
    if (this.contextService.isLoggedIn()) {
      let headers: HttpHeaders = new HttpHeaders()
        .append('Content-Type', 'application/json')
        .append('Authorization', this.contextService.getToken());
      return this.httpClient.get(this.url + 'video/get/' + videoId, { headers });
    } else {
      let headers: HttpHeaders = new HttpHeaders()
        .append('Content-Type', 'application/json');
      return this.httpClient.get(this.url + 'video/get/' + videoId, { headers });
    }
  }

  leaveComment(videoId: number, comment: string): Observable<any> {
    let commentObj = {
      "text": comment
    }
    let params = JSON.stringify(commentObj);
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.post(this.url + 'video/comment/' + videoId, params, { headers });
  }

  likeVideo(videoId: number): Observable<any> {
    let commentObj = {
      "text": 'like'
    }
    let params = JSON.stringify(commentObj);
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.put(this.url + 'video/like/' + videoId, params, { headers });
  }

  unlikeVideo(videoId: number): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.delete(this.url + 'video/dislike/' + videoId, { headers });
  }

  subscribeChannel(channelId: number): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.get(environment.urlChannel + 'channel/subscribe/' + channelId, { headers });
  }

  unsubscribeChannel(channelId: number): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.get(environment.urlChannel + 'channel/unsubscribe/' + channelId, { headers });
  }

}
