import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { GeneralService } from '../service/general.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    errormsg = false;
    validateAllForm = false;
    isLoginBtnDisabled = false;
    lang: any;
    @ViewChild('loginForm') signInForm: NgForm;

    form = new FormGroup({
        username: new FormControl('', Validators.required),
        password: new FormControl('', Validators.required)
    });

  constructor(private router: Router,private generalService: GeneralService, private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  onsubmit() {
    this.isLoginBtnDisabled = true;
    if (this.form.invalid) {
        this.validateAllForm = true;
        this.form.setErrors({...this.form.errors, 'yourErrorName': true});
        document.documentElement.scrollTop = 0;
        return;
    }
    const requestbody = {
        userName: this.signInForm.value.username,
        password: this.signInForm.value.password
    };


    const encodedRequest: string = btoa(JSON.stringify(requestbody));
    const request = {
        encryptedData: encodedRequest
    };
    this.generalService.login(request).subscribe(
        (responseData: any) => {
           localStorage.setItem('session-token', responseData.sessionToken);
           localStorage.setItem('user', JSON.stringify(responseData));
            this.isLoginBtnDisabled = false;
            this.router.navigate(['/sheepframe/home/dashboard']);
        },
        (error: any) => {
           this.errormsg= true;
            this.isLoginBtnDisabled = false;
        }
    );

   
    


}

}
