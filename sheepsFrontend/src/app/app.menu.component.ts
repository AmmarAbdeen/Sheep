import {Component, OnInit} from '@angular/core';
import {AppMainComponent} from './app.main.component';

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html'
})
export class AppMenuComponent implements OnInit {

    model: any[];

    constructor(public app: AppMainComponent) {}

    ngOnInit() {
        this.model = [
            {label: 'لوحة القيادة', icon: 'dashboard', routerLink: ['/sheepframe/dashboard']},
            {
                label: ' القائمة الرئيسية للقطيع', icon: 'list', badge: '2', routerLink: ['/sheepframe/sheepcomponents'], badgeStyleClass: 'orange-badge',
                items: [
                    {label: 'اضافة (فحل / نعجة) جديد', icon: 'add', routerLink: ['/sheepframe/sheepcomponents/addNewSheep']},
                    {label: 'ادارة القطيع', icon: 'search', routerLink: ['/sheepframe/sheepcomponents/managesheep']},
                ]
            },
            {
                label: ' القائمة الرئيسية للمواليد', icon: 'list', badge: '2', routerLink: ['/sheepframe/lambcomponents'], badgeStyleClass: 'orange-badge',
                items: [
                    {label: 'اضافة حمل جديد', icon: 'add', routerLink: ['/sheepframe/lambcomponents/addNewLamb']},
                    {label: 'ادارة المواليد', icon: 'search', routerLink: ['/sheepframe/lambcomponents/managelamb']},
                ]
            },
            {
                label: 'الاعلاف', icon: 'list', badge: '3', routerLink: ['/sheepframe/feedcomponents'], badgeStyleClass: 'orange-badge',
                items: [
                  {label: 'مشتريات الاعلاف ', icon: 'shopping_cart', routerLink: ['/sheepframe/feedcomponents/savefeed']},
                  {label: 'القيمة الغذائية للاعلاف', icon: 'shopping_cart', routerLink: ['/sheepframe/feedcomponents/feedlookups']},
                  {label: 'الاعلاف المخزنة', icon: 'shopping_cart', routerLink: ['/sheepframe/feedcomponents/storedfeed']}
        ]},
        {
            label: 'الحظائر', icon: 'storefront', badge: '2', routerLink: ['/sheepframe/placecomponents'], badgeStyleClass: 'orange-badge',
            items: [
              {label: 'الحظائر', icon: 'storefront', routerLink: ['/sheepframe/placecomponents/places']},
              {label: 'تغذية الحظائر', icon: 'shopping_cart', routerLink: ['/sheepframe/placecomponents/placesfeed']}
    ]},
    {
        label: 'الادوية', icon: 'storefront', badge: '2', routerLink: ['/sheepframe/medicinecomponents'], badgeStyleClass: 'orange-badge',
        items: [
          {label: 'اضافة ادوية', icon: 'storefront', routerLink: ['/sheepframe/medicinecomponents/medicine']},
          {label: 'ادوية امراض القطيع', icon: 'shopping_cart', routerLink: ['/sheepframe/medicinecomponents/sheepmedicine']}
            ]
        },

        {
            label: 'حركة القطيع', icon: 'storefront', badge: '2', routerLink: ['/sheepframe/sheepmovement'], badgeStyleClass: 'orange-badge',
            items: [
              {label: 'حركة القطيع', icon: 'storefront', routerLink: ['/sheepframe/sheepmovement/movement']},
              {label: ' بحث ', icon: 'shopping_cart', routerLink: ['/sheepframe/sheepmovement/movementsearch']}
                ]
            },

        ];
    }

    onMenuClick() {
        this.app.menuClick = true;
    }
}
