// src/app/components/routes/routes.component.ts

import { Component, OnInit } from '@angular/core';
import { Route } from '../../models/route.model';
import { RouteService } from '../../services/route.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-routes',
   standalone: true,

  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './routes.component.html'
})
export class RoutesComponent implements OnInit {

  routes: Route[] = [];
  showForm = false;
  isEdit   = false;
  message  = '';
  error    = '';
  form: Route = this.emptyForm();

  constructor(private routeService: RouteService) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.routeService.getAll().subscribe({
      next:  data => this.routes = data,
      error: err  => this.error = err.error?.error || 'Failed to load routes'
    });
  }

  openAdd(): void { this.form = this.emptyForm(); this.isEdit = false; this.showForm = true; this.clear(); }

  openEdit(r: Route): void { this.form = { ...r }; this.isEdit = true; this.showForm = true; this.clear(); }

  save(): void {
    (this.isEdit ? this.routeService.update(this.form) : this.routeService.add(this.form))
      .subscribe({
        next: () => { this.message = this.isEdit ? 'Route updated!' : 'Route added!'; this.showForm = false; this.load(); },
        error: err => this.error = err.error?.error || 'Save failed'
      });
  }

  delete(id: number): void {
    if (!confirm('Delete route?')) return;
    this.routeService.delete(id).subscribe({
      next: () => { this.message = 'Route deleted!'; this.load(); },
      error: err => this.error = err.error?.error || 'Delete failed'
    });
  }

  cancel(): void { this.showForm = false; this.clear(); }

  private emptyForm(): Route {
    return { routeNumber:'', routeName:'', sourceStop:'', destinationStop:'',
             totalDistanceKm:0, fare:0, totalStops:0, stops:'', isActive:true };
  }

  private clear(): void { this.message = ''; this.error = ''; }
}
