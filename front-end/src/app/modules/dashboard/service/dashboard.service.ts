import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ContextService } from 'src/app/shared/services/context.service';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  url: string;

  constructor(private httpClient: HttpClient, private contextService: ContextService) {
    this.url = environment.urlUser;
  }

  getUsers(pageNum: number, size: number): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.get(this.url + 'user/list?pageNum=' + pageNum + '&size=' + size, { headers });
  }

  banUser(userId: number, isBanned: boolean): Observable<any> {
    let paramObj = {
      text: ''
    };
    let param = JSON.stringify(paramObj);
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.patch(this.url + 'user/ban/' + userId + '?ban=' + isBanned, param, { headers });
  }

  getVideos(pageNum: number, size: number): Observable<any> {
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.get(environment.urlVideo + 'video/list?pageNum=' + pageNum + '&size=' + size, { headers });
  }

  banVideo(videoId: number, isBanned: boolean): Observable<any> {
    let paramObj = {
      text: ''
    };
    let param = JSON.stringify(paramObj);
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Authorization', this.contextService.getToken());
    return this.httpClient.patch(environment.urlVideo + 'video/ban/' + videoId + '?ban=' + isBanned, param, { headers });
  }

}
