import React from 'react';
import PatientForm from '../../../components/PatientForm';
import NavBar from "@/components/NavBar";

const AddPatientPage: React.FC = () => {
    return (
        <div className="container mx-auto p-4">
            <NavBar title={"Ajout Patient"} />
            <PatientForm readOnly={false} classNames={"grid gap-3"}/>
        </div>
    );
};

export default AddPatientPage;