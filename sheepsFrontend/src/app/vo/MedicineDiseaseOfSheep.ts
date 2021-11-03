import { Medicine } from "./Medicine";
import { Sheep } from "./Sheep";

export class MedicineDiseaseOfSheep {

    id: number;
    description: string;
    medicineOnset: any;
    endOfMedicine: any;
    quantity: number;
    disease: string;
    sheep : Sheep;
    medicine : Medicine;
}