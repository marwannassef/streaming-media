import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ContextService } from 'src/app/shared/services/context.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  url: string;

  constructor(private httpClient: HttpClient, private contextService: ContextService) {
    this.url = environment.urlVideo;
  }

  readVideos(): Observable<any> {
      let headers: HttpHeaders = new HttpHeaders()
          .append('Content-Type', 'application/json');
      return this.httpClient.get(this.url + 'video/home', { headers });
  }

}
