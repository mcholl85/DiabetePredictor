'use client'

import React, {useState} from "react";
import Link from "next/link";
import PatientsTable from "@/components/PatientsTable";
import {Button} from "flowbite-react";
import {Patient} from "@/types/patients";
import NoteDetail from "@/components/NoteDetail";
import RiskDetail from "@/components/RiskDetail";
import PatientModal from "@/components/PatientModal";
import PatientDetail from "@/components/PatientDetail";

const Home: React.FC = () => {
    const [selectedPatient, setSelectedPatient] = useState<Patient | undefined>();

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Patients</h1>
            <div className="flex justify-end">
                <Button className="bg-cyan-800">
                    <Link href="/patients/add/">Ajout</Link>
                </Button>
            </div>
            <br/>
            <PatientsTable setSelectedPatient={setSelectedPatient}/>
            <PatientModal selectedPatient={selectedPatient} setSelectedPatient={setSelectedPatient}>
                {selectedPatient && (
                    <>
                        <RiskDetail patId={selectedPatient.id}/>
                        <PatientDetail patient={selectedPatient}/>
                        <NoteDetail
                            patId={selectedPatient.id}
                            patient={selectedPatient.lastName}
                        />
                    </>
                    )}
            </PatientModal>
        </div>
    );
};

export default Home;
