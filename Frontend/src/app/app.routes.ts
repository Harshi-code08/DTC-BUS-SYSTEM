// src/app/app-routing.module.ts

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';

import { RoutesComponent } from './components/routes/routes.component';
import { SchedulesComponent } from './components/schedules/schedules.component';
import { SearchComponent } from './components/search/search.component';
import { BusesComponent } from './components/buses/buses.component';
import { AdminLoginComponent } from './components/admin-login/admin-login.component';


export const routes: Routes = [
  { path: '',          redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'buses',     component: BusesComponent },
  { path: 'routes',    component: RoutesComponent },
  { path: 'schedules', component: SchedulesComponent },
  { path: 'search',    component: SearchComponent },
  {
  path: 'login',
  component: AdminLoginComponent
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
