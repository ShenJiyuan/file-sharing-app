#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <stdio.h>
#include <stdlib.h>
#include <string>

using namespace std;
#define NSIZE  8                            // ÿ�ζ�ȡ�ַ���ĿΪ8

void main()
{
	ifstream ifile;
	ofstream ofile;

	int i = 0, j = 0, iTemp = 0;

	int ibina[NSIZE];       // ��Ŷ������ֽ���
	char cRead;                     // �洢���ļ���������

	ifile.open("D:\Received.txt", ios::in | ios::binary);
	ofile.open("D:\Received_bina.txt");

	if (!ifile)
	{
		cout << "cannot open file\n";
		system("pause");
		return;
	}

	while (!ifile.eof())
	{
		ifile.read(&cRead, 1);

		for (i = 1; i <= NSIZE; i++)
		{
			if ((1 << NSIZE - i) & cRead)
			{
				ibina[i] = 1;
			}
			else
			{
				ibina[i] = 0;
			}

			ofile << ibina[i];
		}
	}

	ifile.close();
	ofile.close();
}