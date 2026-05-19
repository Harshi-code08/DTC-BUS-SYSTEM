import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private API =
    'http://localhost:8080/DTC-bus-system/admin/login';

  constructor(
    private http: HttpClient
  ) {}

  login(
    username: string,
    password: string
  ) {

    const body = {
      username: username,
      password: password
    };

    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(
      this.API,
      body,
      { headers }
    );

  }

}