
#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <stdio.h>
#include <stdlib.h>
#include <string>

using namespace std;

int main()
{
	ifstream ifileIn, ifileOut;

	ifileIn.open("D:/large_bina.txt");
	ifileOut.open("D:/Received_bina.txt");
	int total = 0, error=0;
	while (!ifileIn.eof() && !ifileOut.eof())
	{
		char in, out;
		in = ifileIn.get();
		out = ifileOut.get();
		total++;
		if (in != out) error++;
	}
	while (!ifileIn.eof())
	{
		char temp;
		temp = ifileIn.get();
		total++;
		error++;
	}
	while (!ifileOut.eof())
	{
		char temp;
		temp = ifileOut.get();
		total++;
		error++;
	}
	ifileIn.close();
	ifileOut.close();
	printf("# of error bit:%d / %d\n",error,total);
	system("pause");
	return 0;
}
