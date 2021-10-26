import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService, SelectItem } from 'primeng';
import { GeneralService } from '../service/general.service';

@Component({
  selector: 'app-sheep-management',
  templateUrl: './sheep-management.component.html',
  styleUrls: ['./sheep-management.component.css']
})
export class SheepManagementComponent implements OnInit {
    form = new FormGroup({
    code: new FormControl(),
    color: new FormControl(),
    fromBirthDate: new FormControl(),
    toBirthDate: new FormControl(),
    fromArrivalDate: new FormControl(),
    toArrivalDate: new FormControl(),
    weight: new FormControl(),
    age: new FormControl(),
    shape: new FormControl(),
    status: new FormControl(),

});
    cols: any[];
    sheeps: any[];
@ViewChild('searchForm') searchForm: NgForm;
  statuses: SelectItem[];
  colors: SelectItem[];
  constructor(
    private router: Router,
    private generalService: GeneralService,
    private messageService: MessageService,
    private datePipe: DatePipe) {
    }

  ngOnInit(): void {
    this.statusLookups();
    this.colorLookups();
    this.cols = [
      { field: 'code', header: 'الكود' },
      { field: 'color', header: 'اللون' },
      { field: 'birthDate', header: 'تاريخ الميلاد' },
      { field: 'arrivalDate', header: 'تاريخ الوصول' },
      { field: 'age', header: 'العمر' },
      { field: 'weight', header: 'الوزن' },  
      { field: 'named', header: 'المسمى' },  
      { field: 'shape', header: 'الشكل' },  
      { field: 'status', header: 'الحالة' },  
      { field: 'type', header: 'النوع' },
      { field: 'edit', header: 'تعديل' },
  ];
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

onsubmit(){
          const body = {
            code:  null,
            color: null,
            fromBirthDate:  null,
            toBirthDate: null,
            fromArrivalDate:  null,
            toArrivalDate:  null,
            weight:  null,
            age:  null,
            shape:  null,
            status:  null,
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
      if (this.searchForm.value.fromArrivalDate !== '') {
          body.fromArrivalDate = this.datePipe.transform(this.searchForm.value.fromArrivalDate, 'yyyy-MM-dd HH:mm');
          console.log(body.fromArrivalDate);
        }
      if (this.searchForm.value.toArrivalDate !== '') {
          body.toArrivalDate = this.datePipe.transform(this.searchForm.value.toArrivalDate, 'yyyy-MM-dd 23:59');
          console.log(body.toArrivalDate);
        }
      if (this.searchForm.value.weight !== '') {
        body.weight = this.searchForm.value.weight;
      }
      if (this.searchForm.value.age !== '') {
        body.age = this.searchForm.value.age;
      }
      if (this.searchForm.value.shape !== '') {
        body.shape = this.searchForm.value.shape;
      }
      if (this.searchForm.value.status !== '') {
        body.status = this.searchForm.value.status;
      }

      this.generalService.getSheeps(body).subscribe(
        (responseData: any) => {
            this.sheeps = responseData;
            if (!this.sheeps.length) {
                this.messageService.clear();
                document.documentElement.scrollTop = 0;
                this.messageService.add({ severity: 'error', detail:"لا يوجد قطيع بهذه التفاصيل" });
            } else {
                this.messageService.clear();
            }
        },
        (error: any) => {
                document.documentElement.scrollTop = 0;
                this.messageService.clear();
                this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد قطيع' });
                this.sheeps = [];
            }
    );
}

editSheep(code: any,color: any) {
  this.router.navigate(['sheepframe/sheepcomponents/addNewSheep'], { queryParams: { code,color } });

}

resetData() {
  this.searchForm.reset();
  this.sheeps = [];
  this.messageService.clear();


}

}
