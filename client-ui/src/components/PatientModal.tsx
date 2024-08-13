import {Patient} from "@/types/patients";
import React, {Dispatch, ReactNode, SetStateAction} from "react";
import {Modal} from "flowbite-react";
import useModal from "@/hooks/useModal";

interface PatientModalProps {
    selectedPatient?: Patient
    setSelectedPatient: Dispatch<SetStateAction<Patient | undefined>>
    children: ReactNode
}

const PatientModal: React.FC<PatientModalProps> = ({selectedPatient, setSelectedPatient, children}) => {
    const {closeModal, isOpen} = useModal({patient: selectedPatient, setPatient: setSelectedPatient});

    return <Modal
        className="bg-gray-900/70"
        show={isOpen}
        size="2xl"
        onClose={closeModal}
        title="Patient Details"
    >
        <Modal.Header>
            <h2 className="text-2xl font-bold text-cyan-800">
                Patient Details
            </h2>
        </Modal.Header>
        <Modal.Body className="max-h-[90vh] overflow-y-auto pt-1">
            {isOpen && children}
        </Modal.Body>
    </Modal>
}

export default PatientModal;