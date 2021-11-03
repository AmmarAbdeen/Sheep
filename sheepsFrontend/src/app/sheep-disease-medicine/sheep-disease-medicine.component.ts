import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService, SelectItem } from 'primeng';
import { GeneralService } from '../service/general.service';
import { MedicineDiseaseOfSheep } from '../vo/MedicineDiseaseOfSheep';

@Component({
  selector: 'app-sheep-disease-medicine',
  templateUrl: './sheep-disease-medicine.component.html',
  styleUrls: ['./sheep-disease-medicine.component.css']
})
export class SheepDiseaseMedicineComponent implements OnInit {

  form = new FormGroup({
    description: new FormControl('', [Validators.required]),
    medicineOnset: new FormControl('', [Validators.required]),
    endOfMedicine: new FormControl('', [Validators.required]),
    quantity: new FormControl('', [Validators.required]),
    sheep: new FormControl('', [Validators.required]),
    medicine: new FormControl('', [Validators.required]),
    disease: new FormControl('', [Validators.required])
   
});
medicines: SelectItem[];
sheeps: SelectItem[];
medicineSheep : MedicineDiseaseOfSheep;
validateAllForm = false;
@ViewChild('addForm') saveMedicineDisease: NgForm;
  constructor(
    private router: Router,
    private datePipe: DatePipe,
    private route: ActivatedRoute,
    private generalService: GeneralService,
    private messageService: MessageService
  ) {
    this.medicineSheep = new MedicineDiseaseOfSheep();
   }

  ngOnInit(): void {
    this.getMedicines();
    this.getSheeps();
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
      description: this.medicineSheep.description,
      medicineOnset: this.datePipe.transform(this.medicineSheep.medicineOnset, 'yyyy-MM-dd 00:00'),
      endOfMedicine: this.datePipe.transform(this.medicineSheep.endOfMedicine, 'yyyy-MM-dd 00:00'),
      quantity: this.medicineSheep.quantity,
      disease: this.medicineSheep.disease,
      sheep: this.medicineSheep.sheep,
      medicine: this.medicineSheep.medicine
            };
    this.generalService.saveMedicineSheep(model).subscribe(
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

  getMedicines(){
    this.medicines = [];
    this.generalService.getAllValidMedicine().subscribe(
        (responseData: any) => {
            for (let i = 0; i < responseData.length; i++) {
              this.medicines.push({
                label:responseData[i].name ,
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
