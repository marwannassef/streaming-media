import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ContextService } from 'src/app/shared/services/context.service';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/shared/models/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  url: string;

  constructor(private httpClient: HttpClient, private contextService: ContextService) {
    this.url = environment.urlUser;
  }

  signup(user: User): Observable<any> {
    let params = JSON.stringify(user);
    let headers: HttpHeaders = new HttpHeaders()
      .append('Content-Type', 'application/json');
    return this.httpClient.post(this.url + 'user/signup', params, { headers });
  }

}
