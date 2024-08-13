'use client'

import React, {Dispatch, SetStateAction, useState} from 'react';
import {Button, Pagination, Spinner, Table} from 'flowbite-react';
import {Patient} from "@/types/patients";
import {usePatients} from "@/hooks/usePatients";

interface PatientsTableProps {
    setSelectedPatient: Dispatch<SetStateAction<Patient | undefined>>
}

const PatientsTable: React.FC<PatientsTableProps> = ({setSelectedPatient}) => {
    const [currentPage, setCurrentPage] = useState(0);
    const {data, isLoading} = usePatients(currentPage);
    const patients: Array<Patient> = data?.content || []
    const totalPages = data?.totalPages || 0;

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
                    {isLoading && <Table.Row>
                        <Table.Cell colSpan={5} className="text-center">
                            <Spinner
                                color="info"
                                aria-label="Center-aligned spinner example"
                                size="xl"
                            />
                        </Table.Cell>
                    </Table.Row>}
                    {patients.map((patient) => (
                        <Table.Row key={patient.id} className="bg-white dark:bg-gray-800">
                            <Table.Cell>{patient.firstName}</Table.Cell>
                            <Table.Cell>{patient.lastName}</Table.Cell>
                            <Table.Cell>{patient.birthDate}</Table.Cell>
                            <Table.Cell>{patient.gender}</Table.Cell>
                            <Table.Cell>
                                <Button
                                    onClick={() => setSelectedPatient(patient)}
                                    className="font-medium text-white bg-cyan-800 hover:bg-cyan-500"
                                >
                                    View Details
                                </Button>
                            </Table.Cell>
                        </Table.Row>
                    ))}
                </Table.Body>
            </Table>
            <div className="flex overflow-x-auto sm:justify-center">
                <Pagination currentPage={currentPage + 1} totalPages={totalPages} onPageChange={setCurrentPage} showIcons/>
            </div>
        </div>
    );
};

export default PatientsTable;