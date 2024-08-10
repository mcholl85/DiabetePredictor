import React from 'react';
import {Button, Table} from 'flowbite-react';
import {Patient} from "@/types/patients";

interface PatientsTableProps {
    patients: Patient[] | undefined
    handleOpenModal: (patient: Patient) => void
}

const PatientsTable: React.FC<PatientsTableProps> = ({patients, handleOpenModal}) => {
    return (
        <div className="overflow-x-auto">
            <Table hoverable={true}>
                <Table.Head>
                    <Table.HeadCell>First Name</Table.HeadCell>
                    <Table.HeadCell>Last Name</Table.HeadCell>
                    <Table.HeadCell>Birth Date</Table.HeadCell>
                    <Table.HeadCell>Gender</Table.HeadCell>
                    <Table.HeadCell>
                        <span className="sr-only">Details</span>
                    </Table.HeadCell>
                </Table.Head>
                <Table.Body className="divide-y">
                    {patients?.sort((a,b) => a.id - b.id).map((patient) => (
                        <Table.Row key={patient.id} className="bg-white dark:bg-gray-800">
                            <Table.Cell>{patient.firstName}</Table.Cell>
                            <Table.Cell>{patient.lastName}</Table.Cell>
                            <Table.Cell>{patient.birthDate}</Table.Cell>
                            <Table.Cell>{patient.gender}</Table.Cell>
                            <Table.Cell>
                                <Button
                                    onClick={() => handleOpenModal(patient)}
                                    className="font-medium text-white bg-cyan-800 hover:bg-cyan-500"
                                >
                                    View Details
                                </Button>
                            </Table.Cell>
                        </Table.Row>
                    ))}
                </Table.Body>
            </Table>
        </div>
    );
};

export default PatientsTable;