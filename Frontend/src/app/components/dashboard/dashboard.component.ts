// src/app/components/dashboard/dashboard.component.ts

import { Component, OnInit } from '@angular/core';
import { BusService } from '../../services/bus.service';
import { RouteService } from '../../services/route.service';
import { ScheduleService } from '../../services/schedule.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    RouterLink,       // ← ADD
    RouterLinkActive 
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'

  ] 
})
export class DashboardComponent implements OnInit {

  totalBuses     = 0;
  activeBuses    = 0;
  totalRoutes    = 0;
  activeRoutes   = 0;
  totalSchedules = 0;
  activeSchedules= 0;
  totalStops     = 0;

  constructor(
    private busService:      BusService,
    private routeService:    RouteService,
    private scheduleService: ScheduleService
  ) {}

  ngOnInit(): void {
    this.busService.getAll().subscribe(buses => {
      this.totalBuses  = buses.length;
      this.activeBuses = buses.filter(b => b.isActive).length;
    });
    this.routeService.getAll().subscribe(routes => {
      this.totalRoutes  = routes.length;
      this.activeRoutes = routes.filter(r => r.isActive).length;
      this.totalStops   = routes.reduce((s, r) => s + r.totalStops, 0);
    });
    this.scheduleService.getAll().subscribe(scheds => {
      this.totalSchedules  = scheds.length;
      this.activeSchedules = scheds.filter(s => s.isActive).length;
    });
  }
}
