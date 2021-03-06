PGDMP                         y            JobVXS    13.3    13.3     ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    16557    JobVXS    DATABASE     d   CREATE DATABASE "JobVXS" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'French_France.1252';
    DROP DATABASE "JobVXS";
                postgres    false            q           1247    16559    UserType    TYPE     `   CREATE TYPE public."UserType" AS ENUM (
    'Job Seeker',
    'Company',
    'Administrator'
);
    DROP TYPE public."UserType";
       public          postgres    false            ?            1259    16565    Company    TABLE     ?   CREATE TABLE public."Company" (
    email character varying NOT NULL,
    description character varying NOT NULL,
    address character varying NOT NULL,
    "numberOfEmployee" integer NOT NULL,
    name character varying NOT NULL
);
    DROP TABLE public."Company";
       public         heap    postgres    false            ?            1259    16571 	   JobSeeker    TABLE     ?   CREATE TABLE public."JobSeeker" (
    email character varying NOT NULL,
    name character varying NOT NULL,
    address character varying NOT NULL,
    birthday date NOT NULL,
    description character varying NOT NULL,
    phone integer NOT NULL
);
    DROP TABLE public."JobSeeker";
       public         heap    postgres    false            ?            1259    16577    User    TABLE     ?   CREATE TABLE public."User" (
    email text NOT NULL,
    password character varying NOT NULL,
    type public."UserType" NOT NULL
);
    DROP TABLE public."User";
       public         heap    postgres    false    625            ?          0    16565    Company 
   TABLE DATA           Z   COPY public."Company" (email, description, address, "numberOfEmployee", name) FROM stdin;
    public          postgres    false    200   ?       ?          0    16571 	   JobSeeker 
   TABLE DATA           Y   COPY public."JobSeeker" (email, name, address, birthday, description, phone) FROM stdin;
    public          postgres    false    201   ?       ?          0    16577    User 
   TABLE DATA           7   COPY public."User" (email, password, type) FROM stdin;
    public          postgres    false    202   O       /           2606    16584    Company Company_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public."Company"
    ADD CONSTRAINT "Company_pkey" PRIMARY KEY (email);
 B   ALTER TABLE ONLY public."Company" DROP CONSTRAINT "Company_pkey";
       public            postgres    false    200            1           2606    16586    JobSeeker JobSeeker_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public."JobSeeker"
    ADD CONSTRAINT "JobSeeker_pkey" PRIMARY KEY (email);
 F   ALTER TABLE ONLY public."JobSeeker" DROP CONSTRAINT "JobSeeker_pkey";
       public            postgres    false    201            3           2606    16588    User User_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public."User"
    ADD CONSTRAINT "User_pkey" PRIMARY KEY (email);
 <   ALTER TABLE ONLY public."User" DROP CONSTRAINT "User_pkey";
       public            postgres    false    202            ?   E   x?K??-H̫tH?M???K???L??????LLI)J-.V?OS(?HUH???46??ϩqu??????? ?      ?   K   x???O*NM?N-rH?M???K???̂?q&???+??) ??F????FF??
??
?Ȓf0????? @?Z      ?   L   x?+I-.qH?M???K???,r9Sr3?2?K?K򋸀??y?芜!?\Y?Iũ?٩E?
?????2\1z\\\ B&%?     