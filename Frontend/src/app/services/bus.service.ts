// src/app/services/bus.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Bus } from '../models/bus.model';

@Injectable({ providedIn: 'root' })
export class BusService {

  private readonly API = 'http://localhost:8080/DTC-bus-system/api/buses';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Bus[]> {
    return this.http.get<Bus[]>(this.API);
  }

  getById(id: number): Observable<Bus> {
    return this.http.get<Bus>(`${this.API}?id=${id}`);
  }

  add(bus: Bus): Observable<any> {
    return this.http.post(this.API, bus);
  }

  update(bus: Bus): Observable<any> {
    return this.http.put(this.API, bus);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.API}?id=${id}`);
  }
}
