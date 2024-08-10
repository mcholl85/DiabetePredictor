import {Patient} from "@/types/patients";
import {API_URL} from "@/constants/apiUrl";

const fetchPatients = async (): Promise<Array<Patient>> => {
    const response = await fetch(`${API_URL}/patients`);

    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    return response.json();
};

const fetchPatientById = async (id: number): Promise<Patient> => {
    const response = await fetch(`${API_URL}/patients/${id}`);

    if (!response.ok) {
        throw new Error('Patient not found');
    }
    return response.json();
}

const addPatient = async (newPatient: Omit<Patient, "id">): Promise<Patient> => {
    const response: Response = await fetch(`${API_URL}/patients`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newPatient),
    })

    if (!response.ok) {
        throw new Error('Network response was not ok');
    }

    return response.json();
}

const updatePatient = async (id: number, patientToUpdate: Patient): Promise<Patient> => {
    const response: Response = await fetch(`${API_URL}/patients/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(patientToUpdate),
    })

    if (!response.ok) {
        throw new Error('Network response was not ok');
    }

    return response.json();
}

export {fetchPatients, fetchPatientById, addPatient, updatePatient}