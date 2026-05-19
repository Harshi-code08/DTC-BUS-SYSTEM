// src/app/components/schedules/schedules.component.ts

import { Component, OnInit } from '@angular/core';
import { Schedule } from '../../models/schedule.model';
import { ScheduleService } from '../../services/schedule.service';
import { BusService } from '../../services/bus.service';
import { RouteService } from '../../services/route.service';
import { Bus } from '../../models/bus.model';
import { Route } from '../../models/route.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-schedules',
   standalone: true,

  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './schedules.component.html'
})
export class SchedulesComponent implements OnInit {

  schedules: Schedule[] = [];
  buses: Bus[]          = [];
  routes: Route[]       = [];
  showForm = false;
  isEdit   = false;
  message  = '';
  error    = '';
  form: Schedule = this.emptyForm();

  readonly ALL_DAYS = ['MON','TUE','WED','THU','FRI','SAT','SUN'];
  selectedDays: string[] = ['MON','TUE','WED','THU','FRI'];

  constructor(
    private schedService: ScheduleService,
    private busService: BusService,
    private routeService: RouteService
  ) {}

  ngOnInit(): void {
    this.load();
    this.busService.getAll().subscribe(d => this.buses = d);
    this.routeService.getAll().subscribe(d => this.routes = d);
  }

  load(): void {
    this.schedService.getAll().subscribe({
      next:  d => this.schedules = d,
      error: e => this.error = e.error?.error || 'Load failed'
    });
  }

  openAdd(): void {
    this.form = this.emptyForm();
    this.selectedDays = ['MON','TUE','WED','THU','FRI'];
    this.isEdit = false; this.showForm = true; this.clear();
  }

  openEdit(s: Schedule): void {
    this.form = { ...s };
    this.selectedDays = s.scheduleDays.split(',');
    this.isEdit = true; this.showForm = true; this.clear();
  }

  toggleDay(day: string): void {
    const i = this.selectedDays.indexOf(day);
    i === -1 ? this.selectedDays.push(day) : this.selectedDays.splice(i, 1);
  }

  isDaySelected(day: string): boolean { return this.selectedDays.includes(day); }

  getBusLabel(id: number): string {
    const b = this.buses.find(x => x.id === id);
    return b ? `${b.busNumber} (${b.busType})` : '' + id;
  }

  getRouteLabel(id: number): string {
    const r = this.routes.find(x => x.id === id);
    return r ? `${r.routeNumber}` : '' + id;
  }

  save(): void {
    this.form.scheduleDays = this.selectedDays.join(',') || 'MON';
    (this.isEdit ? this.schedService.update(this.form) : this.schedService.add(this.form))
      .subscribe({
        next: () => { this.message = this.isEdit ? 'Updated!' : 'Added!'; this.showForm = false; this.load(); },
        error: e => this.error = e.error?.error || 'Save failed'
      });
  }

  delete(id: number): void {
    if (!confirm('Delete schedule?')) return;
    this.schedService.delete(id).subscribe({
      next: () => { this.message = 'Deleted!'; this.load(); },
      error: e => this.error = e.error?.error || 'Delete failed'
    });
  }

  cancel(): void { this.showForm = false; this.clear(); }

  private emptyForm(): Schedule {
    return { routeId:0, busId:0, departureTime:'06:00', arrivalTime:'08:00',
             scheduleDays:'MON,TUE,WED,THU,FRI', isActive:true };
  }

  private clear(): void { this.message = ''; this.error = ''; }
}
