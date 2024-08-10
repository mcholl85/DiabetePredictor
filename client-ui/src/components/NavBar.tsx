'use client'
import React from 'react';
import {Button, Navbar} from 'flowbite-react';
import Link from "next/link";

interface NavBarProps {
    title: string
}

const NavBar: React.FC<NavBarProps> = ({title}) => {
    return (
        <Navbar fluid={true} rounded={true} className="mb-4">
            <Navbar.Brand>
                <span className="self-center whitespace-nowrap text-xl font-semibold dark:text-white">
                    {title}
                </span>
            </Navbar.Brand>
            <div className="flex md:order-2">
                <Link href={"/patients"}><Button className="bg-cyan-800 text-white">Retour</Button></Link>
            </div>
        </Navbar>
    );
};

export default NavBar;