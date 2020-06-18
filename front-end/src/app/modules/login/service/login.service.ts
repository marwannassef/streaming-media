import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ContextService } from 'src/app/shared/services/context.service';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { User } from 'src/app/shared/models/user.model';
import * as moment from "moment";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url: string;

  constructor(private httpClient: HttpClient, private contextService: ContextService) {
    this.url = environment.urlUser;
  }

  login(user: User): Observable<any> {
    let params = JSON.stringify(user);
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json');
    return this.httpClient.post(this.url + 'user/authenticate', params, { headers });
  }
          
  setSession(authResult) {
      const expiresAt = moment().add(authResult.expirationDate,'second');
      localStorage.setItem('access_token', authResult.token);
      localStorage.setItem('access_token_expiry', JSON.stringify(expiresAt.valueOf()));
      localStorage.setItem('user_role', authResult.roles[0].authority);
      localStorage.setItem('user_name', authResult.name);
      localStorage.setItem('user_photo', authResult.channelImg);
      localStorage.setItem('user_id', authResult.channelId);
  }

}
