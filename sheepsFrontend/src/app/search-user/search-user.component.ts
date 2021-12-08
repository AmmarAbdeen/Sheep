import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng';
import { GeneralService } from '../service/general.service';

@Component({
  selector: 'app-search-user',
  templateUrl: './search-user.component.html',
  styleUrls: ['./search-user.component.css']
})
export class SearchUserComponent implements OnInit {

cols: any[];
users: any[];
constructor(
  private router: Router,
  private datePipe: DatePipe,
  private route: ActivatedRoute,
  private generalService: GeneralService,
  private messageService: MessageService
) { }

ngOnInit(): void {

  this.cols = [
    { field: 'userName', header: 'اسم المستخدم' },
    { field: 'password', header: 'الرقم السري' },  
    { field: 'fullName', header: 'الاسم كامل' },
    { field: 'mobileNumber', header: 'رقم الموبيل' },
    { field: 'edit', header: 'تعديل' }
];

this.generalService.getAllUsers().subscribe(
  (responseData: any) => {
      this.users = responseData;
      if (!this.users.length) {
          this.messageService.clear();
          document.documentElement.scrollTop = 0;
          this.messageService.add({ severity: 'error', detail:"لا يوجد موظفين مسجلين حتى الان " });
      } else {
          this.messageService.clear();
      }
  },
  (error: any) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد  موظفين مسجلين' });
          this.users = [];
      }
);
}

editUser(id:any){
  this.router.navigate(['home/user/adduser'], { queryParams: { id: id } });
}

}
