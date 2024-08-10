export type Patient = {
    id: number;
    firstName: string;
    lastName: string;
    birthDate: string;
    gender: string;
    address?: string;
    phone?: string;
}