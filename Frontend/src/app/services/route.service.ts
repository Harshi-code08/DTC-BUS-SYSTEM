// src/app/services/route.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Route } from '../models/route.model';

@Injectable({ providedIn: 'root' })
export class RouteService {

  private readonly API = 'http://localhost:8080/DTC-bus-system/api/routes';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Route[]> {
    return this.http.get<Route[]>(this.API);
  }

  getById(id: number): Observable<Route> {
    return this.http.get<Route>(`${this.API}?id=${id}`);
  }

  search(keyword: string): Observable<Route[]> {
    return this.http.get<Route[]>(`${this.API}?search=${encodeURIComponent(keyword)}`);
  }

  add(route: Route): Observable<any> {
    return this.http.post(this.API, route);
  }

  update(route: Route): Observable<any> {
    return this.http.put(this.API, route);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.API}?id=${id}`);
  }
}
