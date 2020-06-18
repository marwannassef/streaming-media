import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ContextService } from 'src/app/shared/services/context.service';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Video } from 'src/app/shared/models/video.model';

@Injectable({
  providedIn: 'root'
})
export class ChannelService {

  url: string;

  constructor(private httpClient: HttpClient, private contextService: ContextService) {
    this.url = environment.urlVideo;
  }

  upload(video: Video): Observable<any> {
    let params = JSON.stringify(video);
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.post(this.url + 'video/upload', params, { headers });
  }

  convertVideo(videoName: string): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.post(this.url + 'video/convert/' + videoName, { headers });
  } 

  getChannel(channelId: number): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json');
    return this.httpClient.get(environment.urlChannel + 'channel/' + channelId, { headers });
  } 

  getUserChannel(): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.get(environment.urlChannel + 'channel', { headers });
  } 

  unsubscribeChannel(channelId: number): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.get(environment.urlChannel + 'channel/unsubscribe/' + channelId, { headers });
  }

  videoStatistics(videoId: number): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.get(environment.urlVideo + 'video/statistics/' + videoId, { headers });
  }

  channelStatistics(): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.get(environment.urlChannel + 'channel/statistics', { headers });
  }

}
