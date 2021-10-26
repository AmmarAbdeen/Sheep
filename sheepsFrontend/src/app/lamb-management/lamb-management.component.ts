import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService, SelectItem } from 'primeng';
import { GeneralService } from '../service/general.service';

@Component({
  selector: 'app-lamb-management',
  templateUrl: './lamb-management.component.html',
  styleUrls: ['./lamb-management.component.css']
})
export class LambManagementComponent implements OnInit {

  form = new FormGroup({
    code: new FormControl(),
    color: new FormControl(),
    fromBirthDate: new FormControl(),
    toBirthDate: new FormControl(),
    fromMatingDate: new FormControl(),
    toMatingDate: new FormControl(),
    weightOfBirth: new FormControl(),
    weightOfWeaning: new FormControl(),
    weightAtSale: new FormControl(),
    age: new FormControl(),
    sheep: new FormControl(),
    status: new FormControl(),

});
cols: any[];
lambs: any[];
@ViewChild('searchForm') searchForm: NgForm;
statuses: SelectItem[];
colors: SelectItem[];
sheeps: SelectItem[];
constructor(
private router: Router,
private generalService: GeneralService,
private messageService: MessageService,
private datePipe: DatePipe) {
}
  
ngOnInit(): void {
    this.getLambsMom();
    this.statusLookups();
    this.colorLookups();
    this.cols = [
      { field: 'code', header: 'الكود' },
      { field: 'color', header: 'اللون' },
      { field: 'birthDate', header: 'تاريخ الميلاد' },
      { field: 'dateOfMating', header: 'تاريخ دخول الفحل' },
      { field: 'age', header: 'العمر' },
      { field: 'weightOfBirth', header: 'الوزن عند الولادة' },  
      { field: 'weightOfWeaning', header: 'الوزن عند الفطام' },  
      { field: 'weightAtSale', header: 'الوزن عند البيع' },  
      { field: 'status', header: 'الحالة' },  
      { field: 'type', header: 'النوع' }, 
      { field: 'sheepDTO', header: 'الام' },
      { field: 'edit', header: 'تعديل' },
  ];
  }

  onsubmit(){
    const body = {
      code:  null,
      color: null,
      fromBirthDate:  null,
      toBirthDate: null,
      fromMatingDate:  null,
      toMatingDate:  null,
      weightOfWeaning:  null,
      weightOfBirth:  null,
      weightAtSale:  null,
      age:  null,
      status:  null,
      sheepDTO: null
  };

if (this.searchForm.value.code !== '') {
    body.code = this.searchForm.value.code;
}
if (this.searchForm.value.color !== '') {
    body.color = this.searchForm.value.color;
}
if (this.searchForm.value.fromBirthDate !== '') {
  body.fromBirthDate = this.datePipe.transform(this.searchForm.value.fromBirthDate, 'yyyy-MM-dd HH:mm');
}
if (this.searchForm.value.toBirthDate !== '') {
  body.toBirthDate = this.datePipe.transform(this.searchForm.value.toBirthDate, 'yyyy-MM-dd 23:59');
}
if (this.searchForm.value.fromMatingDate !== '') {
    body.fromMatingDate = this.datePipe.transform(this.searchForm.value.fromMatingDate, 'yyyy-MM-dd HH:mm');
    console.log(body.fromMatingDate);
  }
if (this.searchForm.value.toMatingDate !== '') {
    body.toMatingDate = this.datePipe.transform(this.searchForm.value.toMatingDate, 'yyyy-MM-dd 23:59');
    console.log(body.toMatingDate);
  }
if (this.searchForm.value.weightOfBirth !== '') {
  body.weightOfBirth = this.searchForm.value.weightOfBirth;
}
if (this.searchForm.value.weightOfWeaning !== '') {
  body.weightOfWeaning = this.searchForm.value.weightOfWeaning;
}
if (this.searchForm.value.weightAtSale !== '') {
  body.weightAtSale = this.searchForm.value.weightAtSale;
}
if (this.searchForm.value.age !== '') {
  body.age = this.searchForm.value.age;
}
if (this.searchForm.value.status !== '') {
  body.status = this.searchForm.value.status;
}
if (this.searchForm.value.sheep !== '') {
  body.sheepDTO = this.searchForm.value.sheep;
}

this.generalService.getLambs(body).subscribe(
  (responseData: any) => {
      this.lambs = responseData;
      if (!this.lambs.length) {
          this.messageService.clear();
          document.documentElement.scrollTop = 0;
          this.messageService.add({ severity: 'error', detail:"لا يوجد اولاد بهذه التفاصيل" });
      } else {
        console.log(responseData);
          this.messageService.clear();
      }
  },
  (error: any) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد اولاد' });
          this.lambs = [];
      }
);
}


statusLookups(){
    this.statuses = [ 
      {label: 'الحالة الاولى', value: 'first'},
      {label: 'الحالة الثانية', value: 'second'},
      ];
}

colorLookups(){
  this.colors = [ 
    {label: 'أحمر', value: 'red'},
    {label: 'أصفر', value: 'yellow'},
    {label: 'أخضر', value: 'green'},
    ];
}

getLambsMom() {
  this.sheeps = [];
  this.generalService.getEwes().subscribe(
      (responseData: any) => {
        console.log(responseData);
          for (let i = 0; i < responseData.length; i++) {
              this.sheeps.push({
                  label: "code : "+responseData[i].code +" , color : "+responseData[i].color,
                  value: responseData[i]
              });
          }
      },
      (error: any) => {
        document.documentElement.scrollTop = 0;
        this.messageService.clear();
        console.log(error);
        this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل الامهات حاول مرة اخرى"});
      }
  );
}

resetData() {
  this.searchForm.reset();
  this.lambs = [];
  this.messageService.clear();


}

editLamb(code: any,color: any) {
  this.router.navigate(['sheepframe/lambcomponents/addNewLamb'], { queryParams: { code,color } });

}

}
