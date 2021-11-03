import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng';
import { GeneralService } from '../service/general.service';
import { Medicine } from '../vo/Medicine';

@Component({
  selector: 'app-medicine',
  templateUrl: './medicine.component.html',
  styleUrls: ['./medicine.component.css']
})
export class MedicineComponent implements OnInit {

  form = new FormGroup({
    name: new FormControl('', [Validators.required]),
    cost: new FormControl('', [Validators.required]),
    quantity: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    expiryDate:new FormControl('', [Validators.required])
   
});
medicines : any[];
cols: any[];
validateAllForm = false;
medicine : Medicine;
@ViewChild('addForm') saveMedicine: NgForm;
constructor(
  private router: Router,
  private datePipe: DatePipe,
  private route: ActivatedRoute,
  private generalService: GeneralService,
  private messageService: MessageService
) {
  this.medicine = new Medicine();
 }

  ngOnInit(): void {

    this.cols = [
      { field: 'name', header: 'اسم الدواء' },
      { field: 'quantity', header: 'الكمية' },
      { field: 'cost', header: 'التكلفة' },  
      { field: 'expiryDate', header: 'تاريخ الانتهاء' }, 
      { field: 'description', header: 'الوصف' }
      
  ];

  this.generalService.getMedicines().subscribe(
    (responseData: any) => {
        this.medicines = responseData;
        if (!this.medicines.length) {
            this.messageService.clear();
            document.documentElement.scrollTop = 0;
            this.messageService.add({ severity: 'error', detail:"لا يوجد أدوية  " });
       } else {
            this.messageService.clear();
        }
   },
   (error: any) => {
            document.documentElement.scrollTop = 0;
            this.messageService.clear();
            this.messageService.add({ severity: 'error', summary: 'Error : ', detail: 'هناك مشكلة لا يوجد أدوية' });
            this.medicines = [];
       }
    );
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
          name: this.medicine.name,
          quantity: this.medicine.quantity,
          cost: this.medicine.cost,
          expiryDate: this.datePipe.transform(this.medicine.expiryDate, 'yyyy-MM-dd 00:00'),
          description: this.medicine.description
            };
    this.generalService.saveMedicine(model).subscribe(
            (response) => {
              document.documentElement.scrollTop = 0;
              this.messageService.clear();
              this.messageService.add({severity: 'success', detail: "تمت الاضافة بنجاح"});
              this.saveMedicine.reset();
             },(error) => {
              document.documentElement.scrollTop = 0;
              this.messageService.clear();
              console.log(error);
              this.messageService.add({severity: 'error', detail: "هناك خطأ في الاضافة حاول مره اخرى  "});
             }
         );
}
}

  cancel() {
    this.router.navigate(['/sheepframe/dashboard']);
  }

}
