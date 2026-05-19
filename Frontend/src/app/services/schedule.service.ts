// src/app/services/schedule.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Schedule } from '../models/schedule.model';

@Injectable({ providedIn: 'root' })
export class ScheduleService {

  private readonly API = 'http://localhost:8080/DTC-bus-system/api/schedules';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Schedule[]> {
    return this.http.get<Schedule[]>(this.API);
  }

  getById(id: number): Observable<Schedule> {
    return this.http.get<Schedule>(`${this.API}?id=${id}`);
  }

  add(schedule: Schedule): Observable<any> {
    return this.http.post(this.API, schedule);
  }

  update(schedule: Schedule): Observable<any> {
    return this.http.put(this.API, schedule);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.API}?id=${id}`)
  }
}
