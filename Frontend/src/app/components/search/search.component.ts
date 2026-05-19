// src/app/components/search/search.component.ts

import { Component } from '@angular/core';
import { Route } from '../../models/route.model';
import { RouteService } from '../../services/route.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search',
   standalone: true,

  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './search.component.html'
})
export class SearchComponent {

  keyword = '';
  results: Route[] = [];
  searched = false;
  error = '';

  constructor(private routeService: RouteService) {}

  search(): void {
    if (!this.keyword.trim()) return;
    this.routeService.search(this.keyword).subscribe({
      next: data => { this.results = data; this.searched = true; this.error = ''; },
      error: err  => this.error = err.error?.error || 'Search failed'
    });
  }
}
