#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <stdio.h>
#include <stdlib.h>
#include <string>

using namespace std;
#define NSIZE  8                            // 每次读取字符数目为8

void main()
{
	ifstream ifile;
	ofstream ofile;

	int i = 0, j = 0, iTemp = 0;

	int ibina[NSIZE];       // 存放二进制字节流
	char cRead;                     // 存储读文件流的数据

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