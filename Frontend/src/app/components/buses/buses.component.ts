// src/app/components/buses/buses.component.ts

import { Component, OnInit } from '@angular/core';
import { BusService } from '../../services/bus.service';
import { Bus } from '../../models/bus.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-buses',
  standalone: true,

  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './buses.component.html'
})
export class BusesComponent implements OnInit {

  buses: Bus[] = [];
  showForm = false;
  isEdit   = false;
  message  = '';
  error    = '';

  form: Bus = this.emptyForm();

  constructor(private busService: BusService) {}

  ngOnInit(): void { this.loadBuses(); }

  loadBuses(): void {
    this.busService.getAll().subscribe({
      next:  data => this.buses = data,
      error: err  => this.error = err.error?.error || 'Failed to load buses'
    });
  }

  openAdd(): void {
    this.form    = this.emptyForm();
    this.isEdit  = false;
    this.showForm = true;
    this.clearMsg();
  }

  openEdit(bus: Bus): void {
    this.form    = { ...bus };
    this.isEdit  = true;
    this.showForm = true;
    this.clearMsg();
  }

  save(): void {
    const call = this.isEdit
      ? this.busService.update(this.form)
      : this.busService.add(this.form);

    call.subscribe({
      next: () => {
        this.message  = this.isEdit ? 'Bus updated!' : 'Bus added!';
        this.showForm = false;
        this.loadBuses();
       
      },
      error: err => this.error = err.error?.error || 'Save failed'
    });
  }

  delete(id: number): void {
    if (!confirm('Delete this bus?')) return;
    this.busService.delete(id).subscribe({
      next: () => { this.message = 'Bus deleted!'; this.loadBuses(); },
      error: err => this.error = err.error?.error || 'Delete failed'
    });
  }

  cancel(): void { this.showForm = false; this.clearMsg(); }

  private emptyForm(): Bus {
    return { busNumber:'', busType:'AC', capacity:50, driverName:'', driverContact:'', isActive:true };
  }

  private clearMsg(): void { this.message = ''; this.error = ''; }
}
