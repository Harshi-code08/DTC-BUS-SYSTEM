// import { Component } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { FormsModule } from '@angular/forms';
// import { Router } from '@angular/router';

// import { AuthService } from '../../services/auth.service';

// @Component({
//   selector: 'app-admin-login',
//   standalone: true,

//   imports: [
//     CommonModule,
//     FormsModule
//   ],

//   templateUrl: './admin-login.component.html',
//   styleUrls: ['./admin-login.component.css'] 

// })
// export class AdminLoginComponent {

//   username = '';
//   password = '';
//   error = '';

//   showPassword = false;   
//   isLoading = false;      

//   constructor(
//     private authService: AuthService,
//     private router: Router
//   ) {}

//   login() {

//     this.authService.login(
//       this.username,
//       this.password
//     ).subscribe({

//       next: (res:any) => {

//         if(res.success){

//           // optional
//           localStorage.setItem("isLoggedIn","true");

//           // dashboard open hoga
//           this.router.navigate(['/dashboard']);

//         } 
//         else {

//           this.error = "Invalid username/password";

//         }

//       },

//       error: () => {

//         this.error = "Server error";

//       }

//     });

//   }

// }

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';  // ← YAHI FIX HAI
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule, FormsModule],  // ← FormsModule yahan add karo
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent {
  username = '';
  password = '';
  error = '';
  showPassword = false;
  isLoading = false;

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.isLoading = true;
    this.authService.login(this.username, this.password).subscribe({
      next: (res: any) => {
        this.isLoading = false;
        if (res.success) {
          localStorage.setItem('isLoggedIn', 'true');
          this.router.navigate(['/dashboard']);
        } else {
          this.error = 'Invalid username ya password';
        }
      },
      error: () => {
        this.isLoading = false;
        this.error = 'Server error';
      }
    });
  }
}