import {dehydrate, QueryClient} from '@tanstack/react-query';
import {GetServerSideProps} from 'next';
import {usePatients} from '@/hooks/usePatients';
import React, {useState} from "react";
import Link from "next/link";
import PatientsTable from "@/components/PatientsTable";
import {Button, Modal} from "flowbite-react";
import {Patient} from "@/types/patients";
import useModal from "@/hooks/useModal";
import PatientDetail from "@/components/PatientDetail";
import {fetchPatients} from "@/api/patient";
import NoteDetail from "@/components/NoteDetail";
import RiskDetail from "@/components/RiskDetail";

const Home: React.FC = () => {
    const [selectedPatient, setSelectedPatient] = useState<Patient | null>(null);
    const {data: patients, error, isLoading} = usePatients();

    const {openModal, closeModal, isOpen} = useModal();

    const handleOpenModal = (patient: Patient) => {
        setSelectedPatient(patient);
        openModal();
    };

    const handleCloseModal = () => {
        closeModal();
        setSelectedPatient(null);
    };

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Patients</h1>
            <div className="flex justify-end">
                <Button className="bg-cyan-800">
                    <Link href="/patients/add/">Ajout</Link>
                </Button>
            </div>
            <br/>
            <PatientsTable patients={patients} handleOpenModal={handleOpenModal}/>
            {isOpen && selectedPatient && (
                <Modal className="bg-gray-900/70" show={isOpen} size="2xl" onClose={handleCloseModal}
                       title="Patient Details">
                    <Modal.Header><h2 className="text-2xl font-bold text-cyan-800">Patient Details</h2>
                    </Modal.Header>
                    <Modal.Body className="max-h-[90vh] overflow-y-auto pt-1">
                        <RiskDetail patId={selectedPatient.id} />
                        <PatientDetail patient={selectedPatient}/>
                        <NoteDetail patId={selectedPatient.id} patient={selectedPatient.lastName}/>
                    </Modal.Body>
                </Modal>
            )}
        </div>
    );
};

export const getServerSideProps: GetServerSideProps = async () => {
    const queryClient = new QueryClient();

    await queryClient.prefetchQuery({queryKey: ['patients'], queryFn: fetchPatients});

    return {
        props: {
            dehydratedState: dehydrate(queryClient),
        },
    };
};

export default Home;