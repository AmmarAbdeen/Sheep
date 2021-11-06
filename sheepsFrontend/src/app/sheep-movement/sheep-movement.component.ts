import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService, SelectItem } from 'primeng';
import { GeneralService } from '../service/general.service';
import { SheepMovement } from '../vo/SheepMovement';

@Component({
  selector: 'app-sheep-movement',
  templateUrl: './sheep-movement.component.html',
  styleUrls: ['./sheep-movement.component.css']
})
export class SheepMovementComponent implements OnInit {

  form = new FormGroup({
    description: new FormControl('', [Validators.required]),
    notes: new FormControl(),
    date: new FormControl('', [Validators.required]),
    sheep: new FormControl(),
    place: new FormControl(),
    lamb: new FormControl()
   
});
lambs: SelectItem[];
sheeps: SelectItem[];
places: SelectItem[];
sheepMovement : SheepMovement;
validateAllForm = false;
@ViewChild('addForm') saveMedicineDisease: NgForm;
  constructor(
    private router: Router,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private generalService: GeneralService,
    private messageService: MessageService
  ) {
    this.sheepMovement = new SheepMovement();
   }
  ngOnInit(): void {
    this.getLambs();
    this.getSheeps();
    this.getPlaces();
  }

  onsubmit(){
    if (this.form.invalid) {
      this.validateAllForm = true;
      this.form.setErrors({ ...this.form.errors, yourErrorName: true });
      document.documentElement.scrollTop = 0;
      this.messageService.clear();
      this.messageService.add({ severity: 'error', detail: "من فضلك ادخل الحقول المطلوبة" });
      return;
     }else  {
    const model = {
      description: this.sheepMovement.description,
      date: this.datePipe.transform(this.sheepMovement.date, 'yyyy-MM-dd 00:00'),
      notes: this.sheepMovement.notes,
      place: this.sheepMovement.place,
      sheep: this.sheepMovement.sheep,
      lamb: this.sheepMovement.lamb
            };
    this.generalService.saveMovements(model).subscribe(
                (response) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  this.messageService.add({severity: 'success', detail: "تمت الاضافة بنجاح"});
                  this.saveMedicineDisease.reset();
                 },(error) => {
                  document.documentElement.scrollTop = 0;
                  this.messageService.clear();
                  console.log(error);
                  this.messageService.add({severity: 'error', detail: "هناك خطأ في الاضافة حاول مره اخرى  "});
                 }
             );
  }
  }

  getPlaces(){
    this.places = [];
    const body = { };
    this.generalService.getPlaces().subscribe(
        (responseData: any) => {
          console.log(responseData);
            for (let i = 0; i < responseData.length; i++) {
                this.places.push({
                    label: responseData[i].code,
                    value: responseData[i]
                });
            }
        },
        (error: any) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          console.log(error);
          this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل الحظائر حاول مرة اخرى"});
        }
    );
  }
  getLambs(){
    this.lambs = [];
    const body = { };
    this.generalService.getLambs(body).subscribe(
        (responseData: any) => {
          console.log(responseData);
            for (let i = 0; i < responseData.length; i++) {
                this.lambs.push({
                    label: "code : "+responseData[i].code +" , color : "+responseData[i].color,
                    value: responseData[i]
                });
            }
        },
        (error: any) => {
          document.documentElement.scrollTop = 0;
          this.messageService.clear();
          console.log(error);
          this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل المواليد حاول مرة اخرى"});
        }
    );
  }

  getSheeps(){
    this.sheeps = [];
    this.generalService.getAllSheeps().subscribe(
        (responseData: any) => {
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
          this.messageService.add({severity: 'error', detail: "هناك مشكلة في تحميل القطيع حاول مرة اخرى"});
        }
    );
  }

  cancel() {
    this.router.navigate(['/sheepframe/dashboard']);
  }

}
