import json

from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt

from kebabmania.models import Kebab, Usuario, Opiniones, Ciudad


@csrf_exempt
def kebabcity (request, cityId):
    # Si el verbo es un get entrara aqui
    if request.method == "GET":

        kebab_all = Kebab.objects.filter(id_ciudad=cityId)




        json_response = []

        for row in kebab_all:


            json_row = row.to_json()


            ciudad_json_row = Ciudad.objects.get(id=json_row['id_ciudad'])
            media = 0
            all_rows = Opiniones.objects.filter(id_kebab=json_row['id'])
            for row_opinion in all_rows:

                opinion_json = row_opinion.to_json()
                media = media + opinion_json['nota']
            if len(all_rows) > 0 and media > 0:
                media = media/len(all_rows)


            json_row['notaMedia'] = media


            json_response.append(json_row)


        return JsonResponse(json_response, safe=False)

    #Metodo no usado pero mantenido para un posible futuri

    if request.method == "POST":
        request_token = request.META.get('HTTP_SESSION_TOKEN')

        if request_token is None:
            return JsonResponse({"error": "Unauthorized"}, status=401)
        try:
            user = Usuario.objects.get(token=request_token)
        except Usuario.DoesNotExist:
            return JsonResponse({"error": "Unauthorized"}, status=401)

        json_body = json.loads(request.body)

        json_nombre = json_body['nombre']
        json_lugar = json_body['lugar']

        if json_nombre is None or json_lugar is None:
            return JsonResponse({'error': 'Missing parameters'}, status=400)

        newKebab = Kebab(nombre=json_nombre,json_lugar=json_lugar, id_ciudad= cityId)

        newKebab.save()

        return JsonResponse({'created': 'True'}, status=201)


    else:
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)